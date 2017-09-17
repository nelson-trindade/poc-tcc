(function(){
    
    var createSalesOrderController = function($scope,ClientDataService,
                                              ProductDataService,SalesService){
        var _next_objId = 1;
        var addObjId = function (v_Object) {
                          if (v_Object == null) return null;
                          if (v_Object._objId == null) v_Object._objId = _next_objId++;
                       };
        
        $scope.clientSearch={},
        $scope.clientSearchList = [];
        $scope.OpenItemFormFlag = false;
        $scope.logisticDoc = {
            client: {},
            items: []
        };

        $scope.selectedDocItem = { _objId: -1 };
        $scope.product = {
            searchError: null
        };
        $scope.productSearchList=[];
        $scope.productSearchDictionary=[];
        $scope.showProductUnitData = false;
        $scope.showProductName = false;

        $scope.searchClient = function(searchTerm){
            if (!searchTerm) {
                $scope.clientSearchList = [];
                return;
            }

            if (searchTerm.length > 3) {
                ClientDataService.getData(searchTerm, 0, 10)
                    .then(OnClientDataRetrivedSuccess, OnClientDataRetrivedError)
            }
        }
        
        var OnClientDataRetrivedSuccess = function(data){
            $scope.clientSearchList=[];
            data.content.forEach(function(item,index,array) {
                $scope.clientSearchList.push(item);
            });
        };

        var OnClientDataRetrivedError = function (reason) {
            $scope.clientSearch.error = reason;
        };

        $scope.clearClientSearchError = function(){
            $scope.clientSearch.error = null;
        };

        $scope.addItem = function(){
            clearItemForm();
            $scope.OpenItemFormFlag = true;
        };

        $scope.OnItemClicked = function (selectedItem) {
            $scope.selectedDocItem = selectedItem;
        };

        $scope.removeItem = function(){
            if (!($scope.selectedDocItem)) {
                $.notify("Selecione um item para excluí-lo","error");
                return;
            }

            var newDocItemList = $scope.logisticDoc.items.filter(function (element) {
                return (element._objId !== $scope.selectedDocItem._objId);
            });

            $scope.logisticDoc.items = newDocItemList;
        };

        $scope.closeItemForm = function(){
            $scope.OpenItemFormFlag=false;
        } 

        var clearItemForm = function(){
            $scope.product = {};
            $scope.showProductName = false;
            $scope.showProductUnitData = false;
            $scope.productSearchDictionary=[];
        };

        $scope.getProductName = function(){
            var searchTerm = $scope.product.searchTerm;
            var product = $scope.productSearchDictionary
                 .find(function(element,index,array){
                      return element.productUUID == searchTerm;
               });
               
            if(product){
                $scope.selectedProduct = product;
                return $scope.selectedProduct.shortName;
            } else {
                return '';
            }
        }

        $scope.showProductInfo = function () {
            if ($scope.product.searchTerm) {
                $scope.showProductUnitData = true;
                $scope.showProductName = true;
            }
        };

        $scope.searchProduct = function(searchTerm){
            if (!searchTerm) {
                $scope.productSearchList = [];
                return;
            }

            if (searchTerm.length > 3) {
                ProductDataService.getData(searchTerm, 0, 10)
                    .then(OnProductDataRetrivedSuccess,OnProductDataRetrivedError);
            }
        };

        var OnProductDataRetrivedSuccess = function(data){
            $scope.productSearchList = [];
            data.content.forEach(function(item,index){
                $scope.productSearchList.push(item);
                $scope.productSearchDictionary.push(item);
            });
        };

        var OnProductDataRetrivedError = function(reason){
            $scope.product.searchError = reason;
        };

        $scope.clearProductSearchError = function(){
            $scope.product.searchError = null;
        };


        $scope.submitItem = function(){
            if (!($scope.clientSearch)) {
                $.notify('Pesquisa de cliente não efetuada.',"error");
                return;
            }

            if (!($scope.clientSearch.searchTerm) || $scope.clientSearch.searchTerm.length == 0) {
                $.notify('Cliente não informado','error');
                return;
            }

            $scope.logisticDoc.client.cpf = String($scope.clientSearch.searchTerm);
            var newDocItem = {
                productUUID: $scope.selectedProduct.productUUID,
                productShortName: $scope.selectedProduct.shortName,
                quantityInput: $scope.product.quantityInput,
                packingUnit: $scope.selectedProduct.packingUnit,
                price : $scope.product.price
            };
            addObjId(newDocItem);
            $scope.logisticDoc.items.push(newDocItem);
            clearItemForm();
        };


        $scope.finishSalesOrder = function(){
            $scope.logisticDoc.items.forEach(function(element,index,array){
                var quantity = element.quantityInput;
                delete element.quantityInput;
                delete element.productShortName;
                delete element.packingUnit;
                element.price = Number(element.price.replace(",","\."));
                element.quantity = quantity;
            });

            SalesService.createSalesOrder({
                docNum:0,
                items: $scope.logisticDoc.items,
                clientCpf: $scope.logisticDoc.client.cpf
            }).then(function(message){
                $('#notificationCallbackMessage')
                  .addClass('alert-success')
                  .removeClass('hidden')
                  .find('span')
                  .text(message.message);
                clearAllPageForm();
            },function(reason){
                console.log(reason.stack);
                $('#notificationCallbackMessage')
                 .addClass('alert-danger')
                 .removeClass('hidden')
                 .find('span')
                 .text(reason.message);
                clearAllPageForm();
            });
        };

        var clearAllPageForm = function(){
            $scope.clientSearch.searchTerm=null;
            $scope.clientSearch.cnpj=null;
            $('#inputClient').val('');

            $scope.clientSearchList = [];
            $scope.logisticDoc = {
                manufacturer: {},
                items: []
            };
        }

        $scope.cleanCallbackMessage = function(){
            $('#notificationCallbackMessage')
              .addClass('hidden')
              .find('span')
              .text('');
        };

    }

    angular.module("pocWebFrontEnd").controller("createSalesOrderController",
                                                ["$scope","ClientDataService",
                                                 "ProductDataService","SalesService",
                                                createSalesOrderController]);
})();