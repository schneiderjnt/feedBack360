var ratingController = function(checkLoginService,$http,$routeParams) {
      var self = this;
	  self.userId=$routeParams.userId;	
	  self.rating={};
	  self.questionnaire={};
	  self.indexQuest=0;
	  self.userSession={};
	  
    self.fetchRating = function() {
      	return $http.get('api/user/'+self.userId+'/rating').then(
            function(response) {
            	self.rating = response.data;
            	self.questionnaire = self.rating.questionnaires[0];
        }, function(errResponse) {
          console.error('Error while fetching notes');
        });
    };
    
    var init = function(){
    	self.fetchRating();
	}
    
    checkLoginService.check(self.userSession,init);
    	
	self.selectQuest = function(){
		self.questionnaire = self.rating.questionnaires[self.indexQuest];
	};	
     
 };

 var answerController = function(checkLoginService,$http,$routeParams) {
     var self = this;
     self.ticketId=$routeParams.ticketId;	
	 self.operation='CREATE';	
     self.questionnaire;
     self.ticket;
	 self.questionnaireAnswer = {answers:[]};
	 self.userSession={};  		  
     var fetchTicket = function() {
	        return $http.get('api/ticket/'+self.ticketId).then(
	            function(response) {
	            	self.questionnaire = response.data.questionnaire;
	            	self.ticket = response.data.ticket;
	        }, function(errResponse) {
	          console.error('Error while fetching notes');
	        });
     };
     
     var init = function(){
    	 fetchTicket();
	} 

     checkLoginService.check(self.userSession,init);
     
     self.save = function() {
		if(self.operation == 'CREATE'){
	        $http.put('api/ticket/'+self.ticketId, self.questionnaireAnswer)
	            .then(function(response) {
	              console.dir(response);
	              window.location='#home';
	         })
		}
     }
   };
   
   var departmentController = function(checkLoginService, $http, $routeParams) {
	      var self = this;
	      self.operation='CREATE';	
	      self.users = [];
		  self.departments = [];	
	      self.newDepartment = {};
	      self.userSession={};
	      var fetchUsers = function() {
	        return $http.get('api/user?company='+self.userSession.session.companyId).then(
	            function(response) {
	            	self.users = response.data;
	        }, function(errResponse) {
	          console.error('Error while fetching notes');
	        });
	      };
	      var fetchDepartment = function() {
		        return $http.get('api/company/'+self.userSession.session.companyId+'/department/details').then(
		            function(response) {
		            	self.departments = response.data;
		        }, function(errResponse) {
		          console.error('Error while fetching notes');
		        });
		      };
		   
	      var init = function(){
	    	  fetchUsers();
		      fetchDepartment();
			}    
		  
	      checkLoginService.check(self.userSession,init);
	     

		  self.beforeEdit = function(department){
				self.newDepartment = department;
				$('#box-user-table').slideToggle();
				$('#box-user-form').slideToggle();
				self.operation = 'UPDATE';
			  };

		  self.beforeCreate = function(){
			  	self.newDepartment = {};
				$('#box-user-table').slideToggle();
				$('#box-user-form').slideToggle();
				self.operation = 'CREATE';
			  };			  		
		  		
	      self.save = function() {
							
			if(self.operation == 'CREATE'){
		        $http.post('api/company/'+self.userSession.session.companyId+'/department', self.newDepartment)
		            .then(function(response) {
		              console.dir(response);
		              fetchDepartment();
//		              $('#box-user-table').slideToggle();
//		  			$('#box-user-form').slideToggle();
		            });
			}else if (self.operation == 'UPDATE'){
				self.newDepartment.manager=null;
				$http.put('api/company/'+self.userSession.session.companyId+'/department', self.newDepartment)
	            .then(function(response) {
	              console.dir(response);
	              fetchDepartment();
//	              $('#box-user-table').slideToggle();
//	  			$('#box-user-form').slideToggle();
	            });
            }

			$('#box-user-table').slideToggle();
			$('#box-user-form').slideToggle();
	      };
	    };
   
    var questionnaireController = function(checkLoginService, $http,$routeParams) {
	      var self = this;
	      self.operation='CREATE';	
	      self.questionnaires = [];
	      self.userSession={};
		  self.questionnaireNew = {title:"",
								description:"",
								companyId:1,
								question:[{text:"",position:1,options:[{text:""}]}]
				};	
		  self.questionnaire = self.questionnaireNew;
	      	var fetchQuestionnaires = function() {
		        return $http.get('api/company/'+self.userSession.session.companyId+'/questionnaire').then(
		            function(response) {
		            	self.questionnaires = response.data;
		        }, function(errResponse) {
		          console.error('Error while fetching notes');
		        });
	      	};
	      	
	      	var init = function(){
	      		fetchQuestionnaires();
			} 
	      	
	      	checkLoginService.check(self.userSession,init);
	      	
		  	self.addQuestion = function(){
				self.questionnaire.question.push({text:"",position:1,options:[{text:""}]});
			};		

			self.removeQuestion = function(qQuestion){
				var qIndex = self.questionnaire.question.indexOf(qQuestion);
				self.questionnaire.question.splice(qIndex,1);
			};	
			
			self.addOption = function(qQuestion){
				qQuestion.options.push({text:""});
			};

			self.removeOption = function(qQuestion,qOption){
				var qIndex = qQuestion.options.indexOf(qOption);
				qQuestion.options.splice(qIndex,1);
			};	
		      
		  	self.beforeEdit = function(quest){
		  		
		  		 $http.get('api/questionnaire/'+quest.id)
		            .then(function(response) {
		            	self.questionnaire = response.data;
		            	$('#box-table').slideToggle();
						$('#box-form').slideToggle();
						self.operation = 'UPDATE';
		            });
		  	};

		  self.beforeCreate = function(){
			  	self.questionnaire = self.questionnaireNew;
				$('#box-table').slideToggle();
				$('#box-form').slideToggle();
				self.operation = 'CREATE';
			  };			  		
		  		
	      self.save = function() {
							
			if(self.operation == 'CREATE'){
				
				self.questionnaire.companyId = self.userSession.session.companyId;
				
		        $http.post('api/questionnaire', self.questionnaire)
		            .then(function(response) {
		              console.dir(response);
		              fetchQuestionnaires();
		            });
			}

			$('#box-table').slideToggle();
			$('#box-form').slideToggle();
	      };
    }
                    
    var ticketController =  function(checkLoginService,$http,$routeParams) {
    	
    	var self = this;
    	self.operation='CREATE';	
	    self.tickets = [];
		self.ticket = {};	
		self.users=[];	
		self.questionnaires = [];
		self.userSession={};
		self.status = {"CREATED":"Aguardando","ANSWERED":"Finalizado"};	
								
      	var fetchTickets = function() {
		        return $http.get('api/company/'+self.userSession.session.companyId+'/ticket').then(
		            function(response) {
		            	self.tickets = response.data;
		        }, function(errResponse) {
		          console.error('Error while fetching notes');
		        });
	      	};
		
		var fetchUsers = function() {
		        return $http.get('api/user?company='+self.userSession.session.companyId).then(
		            function(response) {
		            	self.users = response.data;
		        }, function(errResponse) {
		          console.error('Error while fetching notes');
		        });
		    };

		var fetchQuestionnaires = function(){
				return $http.get('api/company/'+self.userSession.session.companyId+'/questionnaire').then(
		            function(response) {
		            	self.questionnaires = response.data;
		        }, function(errResponse) {
		          console.error('Error while fetching notes');
		        });
			};
		
		var init = function(){
			fetchTickets();
	      	fetchUsers();
	      	fetchQuestionnaires();
		} 
		
		checkLoginService.check(self.userSession,init);
		
		  self.beforeCreate = function(){
			  	self.ticket = {};
				$('#box-table').slideToggle();
				$('#box-form').slideToggle();
				self.operation = 'CREATE'
			  };			  		
		  		
	      self.save = function() {
			if(self.operation == 'CREATE'){
		        $http.post('api/ticket', self.ticket)
		            .then(function(response) {
		              console.dir(response);
		              fetchTickets();
		            });
			}else if (self.operation == 'UPDATE'){
				self.newDepartment.manager=null;
				$http.put('api/company/1/department', self.questionnaire)
	            .then(function(response) {
	              console.dir(response);
	              fetchDepartment();
	            });
            }

			$('#box-table').slideToggle();
			$('#box-form').slideToggle();
	      };
	    };
	    
    var userController = function(checkLoginService,$http,$routeParams) {
	      var self = this;
	      self.operation='CREATE';	
	      self.users = [];
		  self.departments = [];	
	      self.newUser = {};
	      self.userSession={};
	      
	      var fetchUsers = function() {
	        return $http.get('api/user?company='+self.userSession.session.companyId).then(
	            function(response) {
	            	self.users = response.data;
	        }, function(errResponse) {
	          console.error('Error while fetching notes');
	        });
	      };
	      
	      var fetchDepartment = function() {
		        return $http.get('api/company/'+self.userSession.session.companyId+'/department').then(
		            function(response) {
		            	self.departments = response.data;
		        }, function(errResponse) {
		          console.error('Error while fetching notes');
		        });
		      };
		      
	      var init = function(){
	    	  fetchUsers();
		      fetchDepartment();
	      } 
	      
	      checkLoginService.check(self.userSession,init);

		  self.beforeEdit = function(user){
				self.newUser = user;
				$('#box-user-table').slideToggle();
				$('#box-user-form').slideToggle();
				self.operation = 'UPDATE';
			  };

		  self.beforeCreate = function(){
				self.newUser = {}
				$('#box-user-table').slideToggle();
				$('#box-user-form').slideToggle();
				self.operation = 'CREATE';
			  };			  		
		  		
	      self.save = function() {
			
			if(self.operation == 'CREATE'){
		        $http.post('api/user', self.newUser)
		            .then(function(response) {
		              console.dir(response);
		              fetchUsers();
		            });
			}else if (self.operation == 'UPDATE'){
				self.newUser.department=null;
				$http.put('api/user/'+self.newUser.id, self.newUser)
		            .then(function(response) {
		            	console.dir(response);
	            		fetchUsers();
		            });
            }

			$('#box-user-table').slideToggle();
			$('#box-user-form').slideToggle();
	      };
    };
	    
    var homeController = function(checkLoginService,$http,$routeParams) {
      var self = this;
  	  self.tickets=[];
  	  self.userSession={};

  	  self.fetchTickets = function() {
        	return $http.get('api/ticket?fromId='+self.userSession.session.user.id).then(
              function(response) {
            	  self.tickets = response.data;
          }, function(errResponse) {
            console.error('Error while fetching notes');
          });
      };
      
      var init = function(){
      	self.fetchTickets();
  		}
      
      checkLoginService.check(self.userSession,init);
       
   };    
 
 