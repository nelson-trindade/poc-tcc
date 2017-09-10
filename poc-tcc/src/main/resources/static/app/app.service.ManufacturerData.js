(function(){
    
    var ManufacturerDataService = function($http){
       var address = "http://" + location.host +"/manufacturer"; 

        var getData = function(p_SearchTerm,page,size){
            
            return new Promise(function(resolve,reject){
                $http.get(address,{
                    params: {
                        searchTerm: p_SearchTerm,
                        page: page,
                        size: size
                    } 
                }).then(function(response){
                    resolve(response.data);
                },function(error){
                    reject("Erro na comunicação com o servidor");
                });
            });
        };
        
        return{
            getData: getData
        }
    };

    angular.module("pocWebFrontEnd").factory("ManufacturerDataService",ManufacturerDataService);
})();