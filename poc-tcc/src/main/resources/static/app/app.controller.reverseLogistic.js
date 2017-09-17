(function () {

    var mainModule = angular.module("pocWebFrontEnd");

    var reverseLogisticController = function ($scope,
        ManufacturerDataService, ProductDataService, ReverseLogisticService) {

        var _next_objId = 1;
        var addObjId = function (v_Object) {
            if (v_Object == null) return null;
            if (v_Object._objId == null) v_Object._objId = _next_objId++;
        };

        $scope.error = null;
        $scope.notificationCallbackMessage=null;
        $scope.openItemFormFlag = false;
        $scope.showProductName = false;
        $scope.showProductUnitData = false;
        $scope.productSearchDictionary=[];
        $scope.productSearchList = [];
        $scope.returnReasonEnum = [{
            enumValue: 'EXPIRED_DATE',
            description: 'Data de validade'
        },
        {
            enumValue: 'EMPTY_PACKAGE',
            description: 'Embalagem vazia'
        }];

        $scope.ManufacturerSearchList = [];
        $scope.logisticDoc = {
            manufacturer: {},
            items: []
        };

        $scope.selectedDocItem = { _objId: -1 };
        
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
        }

        $scope.searchProduct = function (searchTerm) {
            if (!searchTerm) {
                $scope.productSearchList = [];
                return;
            }

            if (searchTerm.length > 3) {
                ProductDataService.getData(searchTerm, 0, 10)
                    .then(OnProductDataRetrivedSuccess, OnDataRetrivedError)
            }
        };

        var OnProductDataRetrivedSuccess = function (data) {
            $scope.productSearchList = [];
            data.content.forEach(function (item, index) {
                $scope.productSearchList.push(item);
                $scope.productSearchDictionary.push(item);
            });
        };


        $scope.searchManufacturer = function (searchTerm) {
            if (!searchTerm) {
                $scope.ManufacturerSearchList = [];
                return;
            }

            if (searchTerm.length > 3) {
                ManufacturerDataService.getData(searchTerm, 0, 10)
                    .then(OnManufacturerDataRetrivedSuccess, OnDataRetrivedError)
            }
        };

        var OnManufacturerDataRetrivedSuccess = function (data) {
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

        $scope.OnItemClicked = function (selectedItem) {
            $scope.selectedDocItem = selectedItem;
        };

        $scope.removeItem = function () {
            if (!($scope.selectedDocItem)) {
                $scope.error = "Selecione um item para excluí-lo";
                return;
            }

            var newDocItemList = $scope.logisticDoc.items.filter(function (element) {
                return (element._objId !== $scope.selectedDocItem._objId);
            });

            $scope.logisticDoc.items = newDocItemList;
        };
        $scope.closeItemForm = function () {
            $scope.openItemFormFlag = false;
        };

        $scope.submitItem = function () {
            if (!($scope.manufacturer)) {
                $scope.error = 'Pesquisa de fornecedor não efetuada.';
                return;
            }

            if (!($scope.manufacturer.searchTerm) || $scope.manufacturer.searchTerm.length == 0) {
                $scope.error = 'Fornecedor não informado';
                return;
            }

            $scope.logisticDoc.manufacturer.cnpj = String($scope.manufacturer.searchTerm);
            var newDocItem = {
                productUUID: $scope.selectedProduct.productUUID,
                productShortName: $scope.selectedProduct.shortName,
                quantityInput: $scope.product.quantityInput,
                packingUnit: $scope.selectedProduct.packingUnit,
                returnReason: $scope.product.returnReasonSelected,
                expiringDate: Object.keys($scope.product).some(function(value,index,array){
                    return value === 'expiringDate'
                }) ? $scope.product.expiringDate : null
            };
            addObjId(newDocItem);
            $scope.logisticDoc.items.push(newDocItem);
            clearItemForm();
        };

        var clearItemForm = function () {
            $scope.product = {};
            $scope.showProductName = false;
            $scope.showProductUnitData = false;
            $scope.productSearchDictionary=[];
        };

        $scope.clearError = function () {
            $scope.error = null;
        };

        $scope.sendNotification = function(){
            $scope.logisticDoc.items.forEach(function(element,index,array){
                var quantity = element.quantityInput;
                delete element.quantityInput;
                delete element.productShortName;
                delete element.packingUnit;
                element.quantity = quantity;
                element.returnReason = element.returnReason.enumValue;
            });

            ReverseLogisticService.sendReturnableLogisticDoc({
                docNum:0,
                items: $scope.logisticDoc.items,
                manufacturerCnpj: $scope.logisticDoc.manufacturer.cnpj
            })
            .then(function(message){
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
        }

        $scope.clearCallbackNotificationMessage= function(){
            $('#notificationCallbackMessage').addClass('hidden')
                                             .find('span')
                                             .text('');
        }

        var clearAllPageForm = function(){
            $scope.manufacturer.searchTerm=null;
            $scope.manufacturer.cnpj=null;
            $('#inputManufacturer').val('');

            $scope.ManufacturerSearchList = [];
            $scope.logisticDoc = {
                manufacturer: {},
                items: []
            };
        }
    };

    mainModule.controller("reverseLogisticController",
        ["$scope", "ManufacturerDataService", "ProductDataService","ReverseLogisticService"
        , reverseLogisticController]);

})();