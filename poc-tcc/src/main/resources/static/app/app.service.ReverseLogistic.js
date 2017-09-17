(function(){
    
    var ReverseLogisticService = function($http){
        var address = "http://" + location.host + '/returnLogisticDoc';

        var sendReturnableLogisticDoc = function(LogisticDocMessage){
            return new Promise(function(resolve,reject){
                $http.post(address,LogisticDocMessage)
                .then(function(response){
                    resolve(response.data);
                },function(reason){
                    reject(reason);
                });
            });
        };

        return {
            sendReturnableLogisticDoc: sendReturnableLogisticDoc
        };

    };

    angular.module("pocWebFrontEnd").factory("ReverseLogisticService",ReverseLogisticService);

})();