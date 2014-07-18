var a={
	"groupApps" : {
		"0" : {
			"name" : "Group1",
			"rowId" : "1",
			"relationship" : [ {
				"name" : "Apps",
				"rowId" : "0"
			} ]
		}
	},
	"apps" : {
		"0" : {
			"name" : "Common",
			"rowId" : "1",
			"sort" : "1"
		},
		"1" : {
			"name" : "App1"
		}
	},
	"relationship" : {
		"0" : {
			"type" : "groupAppsAndapps",
			"relation" : [ {
				"r" : "0",
				"c" : "0",
				"s" : "0"
			}, {
				"r" : "0",
				"c" : "1",
				"s" : "1"
			} ]
		}
	},
	"action" : {
		"A" : {
			"name" : "main",
			"f" : function() {
				alert("main");
			}
		}
	}
};
