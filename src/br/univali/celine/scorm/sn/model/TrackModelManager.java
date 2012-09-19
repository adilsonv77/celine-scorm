package br.univali.celine.scorm.sn.model;

import java.util.Collection;
import java.util.logging.Logger;

import br.univali.celine.lmsscorm.Course;
import br.univali.celine.lmsscorm.DAO;
import br.univali.celine.lmsscorm.TrackActivityInfo;
import br.univali.celine.lmsscorm.TrackModel;
import br.univali.celine.lmsscorm.TrackObjective;
import br.univali.celine.scorm.dataModel.adl.nav.Request;
import br.univali.celine.scorm.sn.model.interaction.Interaction;

public class TrackModelManager {

	private Logger logger = Logger.getLogger("global");
	
	private ActivityTree activityTree;
	private String learnerId;

	private String courseFolder;
	
	public TrackModelManager(ActivityTree activityTree, String learnerId, String courseFolder) {
		this.activityTree = activityTree;
		this.learnerId = learnerId;
		this.courseFolder = courseFolder;
	}
	
	public void save(DAO dao) throws Exception {
		
		Course course = dao.findCourseByFolderName(courseFolder);
		logger.info(activityTree.getDataModel(Request.name));
		String courseId = course.getId();
		
		/* 
		 * Minha interpretacao: nao está boa essa interpretacao
		 * + ExitAll: destroi com o TM
		 * + AbandonAll: nao modifica o TM
		 * + SuspendAll: modifica o TM
		 * 
		 */
		/*
		if (Request.isRequest(activityTree, NavigationRequestType.ExitAll)) {

			//dao.removeTrackModel(courseId, learnerId);
			
		} else {
*/
			if (Request.isRequest(activityTree, NavigationRequestType.AbandonAll) == false ) {
				logger.info("saving trackmodel");
				String suspendedActivity = null;
				if (activityTree.getSuspendActivity() != null) {
					suspendedActivity = activityTree.getSuspendActivity().getItem().getIdentifier();
				}
				
				dao.beginSaveTrackModel(courseId, 
										learnerId, 
										suspendedActivity);
		
				saveObjectives(courseId, learnerId, dao, null, activityTree.getGlobalObjectives());
				
				// precisa gravar a partir do root, pois existem rollup rules que foram executadas !!!
				iterateActivities(courseId, learnerId, dao, activityTree.getRootActivity(), null, 0); 
				
				dao.finishSaveTrackModel();
			}
			
	//	}
		
	}

	private void saveObjectives(String courseId, String learnerId, DAO dao, String actId, Collection<? extends Objective> objectives) throws Exception {

		for (Objective obj:objectives) {
			
			dao.saveObjectiveProgressInformation(
					courseId, learnerId,
					actId,
					obj.getObjectiveID(), obj.isProgressStatus(), obj.isSatisfiedStatus(), 
					obj.isMeasureStatus(), obj.getNormalizedMeasure());
			
		}
	}

	private void iterateActivities(String courseId, String learnerId, DAO dao, LearningActivity act, String parentActId, int idxChildren) throws Exception {
		/*
		 * deixei isso em comentário para lembrar que nao posso fazer isso... tem os available children que já foram
		 *   selecionados quando o usuário entrou. Como pode ser que eles nunca foram tentados, quando o sistema
		 *   recarregar a árvore eles nao estarao mais disponíveis :( 
		 *    
		if (act.getAttemptCount() == 0) {
			// atividades que nao foram tentadas nao precisam ser armazenadas ;)
			return;
		}
		*/
		
		logger.info("CompletionStatus : " + act.getItem().getIdentifier() + " " + act.getCompletionStatus());
		
		dao.saveActivityProgressInformation(
				courseId, learnerId,
				act.getItem().getIdentifier(), 
				parentActId, idxChildren,
				act.isProgressStatus(),
				act.getActivityAbsoluteDuration(),
				act.getExperiencedDuration(),
				act.getAttemptCount(),
				act.isSuspended(),
				act.isAttemptCompletionStatus(),
				act.isAttemptProgressStatus(),
				act.getCompletionStatus().toString(),
				act.getSuccessStatus().toString(),
				act.getScoreRaw(),
				act.getScoreMin(),
				act.getScoreMax(),
				act.getScoreScaled());
		
		saveObjectives(courseId, learnerId, dao, act.getItem().getIdentifier(), act.getObjectives());

		int index = 0;
		for (Interaction interact:act.getInteractions()) {
			dao.beginSaveInteractionInformation(courseId, learnerId, act.getItem().getIdentifier(),
					index, interact.getId(), interact.getType(), interact.getTimeStamp(), interact.getWeighting(),
					interact.getLearnerResponse(), interact.getResult(), interact.getLatency(), interact.getDescription());
					
			dao.finishSaveInteractionInformation(courseId, learnerId, act.getItem().getIdentifier(),
					index, interact.getId());
			index++;
		}
		
		int idx = 0;
		for (LearningActivity activity:act.getAvailableChildren()) { // modificado 18/09/12 para getAvailableChildren, pois só interessa manter os disponiveis
			iterateActivities(courseId, learnerId, dao, activity, act.getItem().getIdentifier(), idx);
			idx++;
		}
			
	} 
	
	public String load(DAO dao) throws Exception {
		
		logger.info(courseFolder);
		Course course = dao.findCourseByFolderName(courseFolder);
		
		logger.info(""+course);

		// as interactions não são carregadas por nao fazerem parte do TrackModel... essa é uma informacao que é guardada
		//  única e exclusivamente para manter um registro do que o usuário preencheu nos exercicios
		TrackModel trackModel = dao.loadTrackModel(course.getId(), learnerId);
		logger.info("load : " + trackModel);
		
		if (trackModel.getSuspendedActivity() != null) {
			if (activityTree.getLearningActivity(trackModel.getSuspendedActivity()) == null) {
				return null; // já que nao achou a atividade suspensa, é melhor nem continuar essa palhaçada
			}
		}
		
		for (TrackActivityInfo trackAct:trackModel.getActivities()) {
			
			logger.info("trackAct : " + trackAct);
			
			LearningActivity act = activityTree.getLearningActivity(trackAct.getId());
			if (act != null) { // acontece em cursos dinamicos, em que as atividades ora aparecem e ora desaparecem
				act.setSuspended(trackAct.isSuspended());
				act.setProgressStatus(trackAct.isProgressStatus());
				act.setAttemptCount(trackAct.getAttemptCount());
				act.setActivityAbsoluteDuration(trackAct.getAbsoluteDuration());
				act.setExperiencedDuration(trackAct.getExperiencedDuration());
				act.setAttemptCompletionStatus(trackAct.isAttemptCompletionStatus());
				act.setAttemptProgressStatus(trackAct.isAttemptProgressStatus());
				act.setScoreRaw(trackAct.getScoreRaw());
				act.setScoreMin(trackAct.getScoreMin());
				act.setScoreMax(trackAct.getScoreMax());
				act.setScoreScaled(trackAct.getScoreScaled());
				
				if (trackAct.getParentId() != null) {
					activityTree.getLearningActivity(trackAct.getParentId()).getAvailableChildren().add(act);
				}
			}
			
		}
		
		for (TrackObjective obj:trackModel.getObjectives()) {
			
			if (obj.getActivityID() == null) { // objetivo global
				GlobalObjective global = activityTree.getGlobalObjective(obj.getObjectiveID());
				populateObjective(global, obj);
			} else {
			
				LearningActivity act = activityTree.getLearningActivity(obj.getActivityID());
				if (act != null) {
					LocalObjective actobj = act.getObjective(obj.getObjectiveID());
					if (actobj == null) { // objetivo criado dinamicamente, ou seja, durante o curso
						actobj = new DynamicObjective(obj.getObjectiveID());
					}
					populateObjective(actobj, obj);
				}
				
			}
		}
		
		return trackModel.getSuspendedActivity();
	}

	private void populateObjective(Objective actobj, TrackObjective obj) {
		if (obj.isMeasureStatus()) {
			actobj.setMeasureStatus(obj.isMeasureStatus());
			actobj.setNormalizedMeasure(obj.getNormalizedMeasure());
		}
		
		actobj.setProgressStatus(obj.isProgressStatus());
		actobj.setSatisfiedStatus(obj.isSatisfiedStatus());
	}
}
