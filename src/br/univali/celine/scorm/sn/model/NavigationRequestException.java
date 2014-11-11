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
package br.univali.celine.scorm.sn.model;

@SuppressWarnings("serial")
public class NavigationRequestException extends Exception {

	private int exception;

	public NavigationRequestException(int exception) {
		this.exception = exception;
	}

	public int getException() {
		return exception;
	}

	public String toString() {
		return msgs[exception];
	}

	// TODO transformar em descendentes de Exception
	private static final String[] msgs = new String[] {
		"",
		"NB.2.1-1 Current Activity is already defined / Sequencing session has already begun",
		"NB.2.1-2 Current Activity is not defined / Sequencing session has not begun",
		"NB.2.1-3 Suspended Activity is not defined",
		"NB.2.1-4 Flow Sequencing Control Mode violation",
		"NB.2.1-5 Flow or Forward Only Sequencing Control Mode violation",
		"NB.2.1-6 No activity is \"previous\" to the root",
		"NB.2.1-7 Unsupported navigation request",
		"NB.2.1-8 Choice Exit Sequencing Control Mode violation",
		"NB.2.1-9 No activities to consider",
		"NB.2.1-10 Choice Sequencing Control Mode violation",
		"NB.2.1-11 Target activity does not exist",
		"NB.2.1-12 Current Activity already terminated",
		"NB.2.1-13 Undefined navigation request",
		"TB.2.3-1 Current Activity is not defined / Sequencing session has not begun",
		"TB.2.3-2 Current Activity already terminated",
		"TB.2.3-3 Cannot suspend an inactive root",
		"TB.2.3-4 Activity tree root has no parent",
		"TB.2.3-5 Nothing to suspend; No active activities",
		"TB.2.3-6 Nothing to abandon; No active activities",
		"TB.2.3-7 Undefined termination request",
		"SB.2.1-1 Last activity in the tree",
		"SB.2.1-2 Cluster has no available children",
		"SB.2.1-3 No activity is \"previous\" to the root",
		"SB.2.1-4 Forward Only Sequencing Control Mode violation",
		"SB.2.2-1 Flow Sequencing Control Mode violation",
		"SB.2.2-2 Activity unavailable",
		"SB.2.4-1 Forward Traversal Blocked",
		"SB.2.4-2 Forward Only Sequencing Control Mode violation",
		"SB.2.4-3 No activity is \"previous\" to the root",
		"SB.2.5-1 Current Activity is defined / Sequencing session already begun",
		"SB.2.6-1 Current Activity is defined / Sequencing session already begun",
		"SB.2.6-2 No Suspended Activity defined",
		"SB.2.7-1 Current Activity is not defined / Sequencing session has not begun",
		"SB.2.7-2 Flow Sequencing Control Mode violation",
		"SB.2.8-1 Current Activity is not defined / Sequencing session has not begun",
		"SB.2.8-2 Flow Sequencing Control Mode violation",
		"SB.2.9-1 No target for Choice",
		"SB.2.9-2 Target activity does not exist or is unavailable",
		"SB.2.9.3 Target activity hidden from choice",
		"SB.2.9-4 Choice Sequencing Control Mode violation",
		"SB.2.9-5 No activities to consider",
		"SB.2.9-6 Unable to activate target; target is not a child of the Current Activity",
		"SB.2.9-7 Choice Exit Sequencing Control Mode violation",
		"SB.2.9-8 Unable to choose target activity - constrained choice",
		"SB.2.9-9 Choice request prevented by Flow-only activity",
		"SB.2.10-1 Current Activity is not defined / Sequencing session has not begun",
		"SB.2.10-2 Current Activity is active or suspended",
		"SB.2.10-3 Flow Sequencing Control Mode violation",
		"SB.2.11-1 Current Activity is not defined / Sequencing session has not begun",
		"SB.2.11-2 Current Activity has not been terminated",
		"SB.2.12-1 Undefined sequencing request",
		"DB.1.1-1 Cannot deliver a non-leaf activity",
		"DB.1.1-2 Nothing to deliver",
		"DB.1.1-3 Activity unavailable",
		"DB.2-1 Identified activity is already active",
		"SB.2.12-1 Current Activity is not defined / Sequencing session has not begun"
		
	};
	
	/*
	 * 
1 NB.2.1-1 Current Activity is already defined / Sequencing session has already begun
2 NB.2.1-2 Current Activity is not defined / Sequencing session has not begun
3 NB.2.1-3 Suspended Activity is not defined
4 NB.2.1-4 Flow Sequencing Control Mode violation
5 NB.2.1-5 Flow or Forward Only Sequencing Control Mode violation
6 NB.2.1-6 No activity is “previous” to the root
7 NB.2.1-7 Unsupported navigation request
8 NB.2.1-8 Choice Exit Sequencing Control Mode violation
9 NB.2.1-9 No activities to consider
10 NB.2.1-10 Choice Sequencing Control Mode violation
11 NB.2.1-11 Target activity does not exist
12 NB.2.1-12 Current Activity already terminated
13 NB.2.1-13 Undefined navigation request
14 TB.2.3-1 Current Activity is not defined / Sequencing session has not begun
15 TB.2.3-2 Current Activity already terminated
16 TB.2.3-3 Cannot suspend an inactive root
17 TB.2.3-4 Activity tree root has no parent
18 TB.2.3-5 Nothing to suspend; No active activities
19 TB.2.3-6 Nothing to abandon; No active activities
20 TB.2.3-7 Undefined termination request
21 SB.2.1-1 Last activity in the tree
22 SB.2.1-2 Cluster has no available children
23 SB.2.1-3 No activity is “previous” to the root
24 SB.2.1-4 Forward Only Sequencing Control Mode violation
25 SB.2.2-1 Flow Sequencing Control Mode violation
26 SB.2.2-2 Activity unavailable
27 SB.2.4-1 Forward Traversal Blocked
28 SB.2.4-2 Forward Only Sequencing Control Mode violation
29 SB.2.4-3 No activity is “previous” to the root
30 SB.2.5-1 Current Activity is defined / Sequencing session already begun
31 SB.2.6-1 Current Activity is defined / Sequencing session already begun
32 SB.2.6-2 No Suspended Activity defined
33 SB.2.7-1 Current Activity is not defined / Sequencing session has not begun
34 SB.2.7-2 Flow Sequencing Control Mode violation
35 SB.2.8-1 Current Activity is not defined / Sequencing session has not begun
36 SB.2.8-2 Flow Sequencing Control Mode violation
37 SB.2.9-1 No target for Choice
38 SB.2.9-2 Target activity does not exist or is unavailable
39 SB.2.9.3 Target activity hidden from choice
40 SB.2.9-4 Choice Sequencing Control Mode violation
41 SB.2.9-5 No activities to consider
42 SB.2.9-6 Unable to activate target; target is not a child of the Current Activity
43 SB.2.9-7 Choice Exit Sequencing Control Mode violation
44 SB.2.9-8 Unable to choose target activity – constrained choice
45 SB.2.9-9 Choice request prevented by Flow-only activity
46 SB.2.10-1 Current Activity is not defined / Sequencing session has not begun
47 SB.2.10-2 Current Activity is active or suspended
48 SB.2.10-3 Flow Sequencing Control Mode violation
49 SB.2.11-1 Current Activity is not defined / Sequencing session has not begun
50 SB.2.11-2 Current Activity has not been terminated
51 SB.2.12-1 Undefined sequencing request
52 DB.1.1-1 Cannot deliver a non-leaf activity
53 DB.1.1-2 Nothing to deliver
54 DB.1.1-3 Activity unavailable
55 DB.2-1 Identified activity is already active
	 */
	
}
