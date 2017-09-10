(function () {

    var mainModule = angular.module("pocWebFrontEnd");

    var reverseLogisticController = function ($scope, ManufacturerDataService) {
        
        var _next_objId = 1;
        var addObjId = function(v_Object){
            if(v_Object==null) return null;
            if(v_Object._objId==null) v_Object._objId = _next_objId++;
        };
    
        $scope.error = null;
        $scope.openItemFormFlag = false;
        $scope.product = {}
        $scope.returnReasonEnum = [{
            enumValue: 'EXPIRED_DATE',
            description:'Data de validade'
        },
        {
           enumValue:'EMPTY_PACKAGE',
           description:'Embalagem vazia' 
        }];

        $scope.product.measureUnit = 'KG';
        $scope.ManufacturerSearchList = [];
        $scope.logisticDoc={
            manufacturer: {},
            items:[]
        };
        
        $scope.selectedDocItem={_objId:-1};

        $scope.search = function (searchTerm) {
            if(!searchTerm)
            {
                $scope.ManufacturerSearchList=[];
                return;
            }   

            if (searchTerm.length > 3) {
                ManufacturerDataService.getData(searchTerm, 0, 10)
                    .then(OnDataRetrivedSuccess, OnDataRetrivedError)
            }
        };

        var OnDataRetrivedSuccess = function (data) {
            $scope.ManufacturerSearchList = [];
            data.content.forEach(function (item, index) {
                $scope.ManufacturerSearchList.push(item);
            });
        };

        var OnDataRetrivedError = function (reason) {
            $scope.error = reason;
        };

        $scope.addItem = function () {
            clearItemForm();
            $scope.openItemFormFlag = true;
        };

        $scope.OnItemClicked = function(selectedItem){
            $scope.selectedDocItem = selectedItem;
        };

        $scope.removeItem = function(){
           if(!($scope.selectedDocItem)){
               $scope.error = "Selecione um item para excluí-lo";
               return;
           }
            
           var newDocItemList = $scope.logisticDoc.items.filter(function(element){
               return (element._objId !== $scope.selectedDocItem._objId);
           });

           $scope.logisticDoc.items = newDocItemList;
        };

        $scope.closeItemForm = function () {
            $scope.openItemFormFlag = false;
        };

        $scope.submitItem = function(){
            if(!($scope.manufacturer))
            {
                $scope.error = 'Pesquisa de fornecedor não efetuada.';
                return;
            }

            if(!($scope.manufacturer.searchTerm) || $scope.manufacturer.searchTerm.length==0){
                $scope.error = 'Fornecedor não informado';
                return;
            }

            $scope.logisticDoc.manufacturer.cnpj = $scope.manufacturer.searchTerm;
            var newDocItem = {
                productShortName: $scope.product.searchTerm,
                quantity: $scope.product.quantity +' '+ $scope.product.measureUnit,
                returnReason: $scope.product.returnReasonSelected
            };
            addObjId(newDocItem);
            $scope.logisticDoc.items.push(newDocItem);
            clearItemForm();
        };
        
        var clearItemForm = function(){
            $scope.product= {};
            $scope.product.measureUnit = 'KG';
        };

        $scope.clearError = function () {
            $scope.error = null;
        };
    };

    mainModule.controller("reverseLogisticController",
        ["$scope", "ManufacturerDataService", reverseLogisticController]);

})();