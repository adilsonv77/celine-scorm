/**
 * CELINE SCORM
 *
 * Copyright 2014 Adilson Vahldick.
 * https://celine-scorm.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*******************************************************************************
**
** Advanced Distributed Learning Co-Laboratory (ADL Co-Lab) grants you
** ("Licensee") a non-exclusive, royalty free, license to use and redistribute
** this software in source and binary code form, provided that i) this copyright
** notice and license appear on all copies of the software; and ii) Licensee
** does not utilize the software in a manner which is disparaging to ADL Co-Lab.
**
** This software is provided "AS IS," without a warranty of any kind.  ALL
** EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
** IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
** NON-INFRINGEMENT, ARE HEREBY EXCLUDED.  ADL Co-Lab AND ITS LICENSORS SHALL
** NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
** MODIFYING OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES.  IN NO EVENT WILL
** ADL Co-Lab OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA,
** OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
** DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
** OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF ADL Co-Lab HAS BEEN
** ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
**
*******************************************************************************/

/*******************************************************************************
**
** This file is being presented to Content Developers, Content Programmers and
** Instructional Designers to demonstrate one way to abstract API calls from the
** actual content to allow for uniformity and reuse of content fragments.
**
** The purpose in wrapping the calls to the API is to (1) provide a
** consistent means of finding the LMS API adapter within the window
** hierarchy, (2) to ensure that the method calls are called correctly by the
** SCO and (3) to make possible changes to the actual API Specifications and
** Standards easier to implement/change quickly.
**
** This is just one possible example for implementing the API guidelines for
** runtime communication between an LMS and executable content components.
** There are many other possible implementations.
**
*******************************************************************************/

/*******************************************************************************
**
** Minha adaptação. A TOC precisa se comunicar com o LMS através do SCO. Para 
** isso preciso de umas funcoes. Acessá-las pelo próprio SCO não é garantido, visto
** que cada Content Developer pode usar seu próprio wrapper!!! 
** 
** Nesse wrapper evito de ter controles. Tomara que isso funcione !!!
** Possuo somente algumas funcoes e bem enxutas.
**
*******************************************************************************/

// local variable definitions used for finding the API
var apiHandle = null;
var findAPITries = 0;
var noAPIFound = "false";

/*******************************************************************************
**
** This function looks for an object named API in parent and opener windows
**
** Inputs:  Object - The Window Object
**
** Return:  Object - If the API object is found, it's returned, otherwise null
**          is returned
**
*******************************************************************************/
function findAPI( win )
{
   while ( (win.API_1484_11 == null) &&
           (win.parent != null) &&
           (win.parent != win) )
   {
      findAPITries++;

      if ( findAPITries > 500 )
      {
         alert( "Error finding API -- too deeply nested." );
         return null;
      }

      win = win.parent;
   }

   return win.API_1484_11;
}

/*******************************************************************************
**
** This function looks for an object named API, first in the current window's
** frame hierarchy and then, if necessary, in the current window's opener window
** hierarchy (if there is an opener window).
**
** Inputs:  none
**
** Return:  Object - If the API object is found, it's returned, otherwise null
**                   is returned
**
*******************************************************************************/
function getAPI()
{
   var theAPI = findAPI( window );

   if ( (theAPI == null) &&
        (window.opener != null) &&
        (typeof(window.opener) != "undefined") )
   {
      theAPI = findAPI( window.opener );
   }

   if (theAPI == null)
   {
      alert( "Unable to locate the LMS's API Implementation.\n" +
             "Communication with the LMS will not occur." );

      noAPIFound = "true";
   }

   return theAPI
}

/*******************************************************************************
**
** Returns the handle to API object if it was previously set, otherwise it
** returns null
**
** Inputs:  None
**
** Return:  Object - The value contained by the apiHandle variable.
**
*******************************************************************************/
function getAPIHandle()
{
   if ( apiHandle == null )
   {
      if ( noAPIFound == "false" )
      {
         apiHandle = getAPI();
      }
   }

   return apiHandle;
}


/*******************************************************************************
**
** This function is used to tell the LMS to terminate the communication session
**
** Inputs:  None
**
** Return:  String - "true" if successful or
**                   "false" if failed.
**
*******************************************************************************/
function terminateCommunication()
{
   var api = getAPIHandle();

   if ( api == null )
   {
      return "false";
   }
   else
   {
       return api.Terminate("");

   }

}

/*******************************************************************************
**
** This function requests information from the LMS.
**
** Inputs:  String - Name of the data model defined category or element
**                   (e.g. cmi.core.learner_id)
**
** Return:  String - The value presently assigned to the specified data model
**                   element.
**
*******************************************************************************/
function retrieveDataValue( name )
{
      var api = getAPIHandle();

      if ( api == null )
      {
         return "";
      }
      else
      {
         return api.GetValue( name );

      }

}

/*******************************************************************************
**
** This function is used to tell the LMS to assign the value to the named data
** model element.
**
** Inputs:  String - Name of the data model defined category or element value
**
**          String - The value that the named element or category will be
**                   assigned
**
** Return:  String - "true" if successful or
**                   "false" if failed.
**
*******************************************************************************/
function storeDataValue( name, value )
{
      var api = getAPIHandle();

      if ( api == null )
      {
         return;
      }
      else
      {
         return api.SetValue( name, value );

      }

}
