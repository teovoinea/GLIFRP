var main_marker = L.marker([0,0]);
(function(){
  var app = angular.module('GLIFRP', [
    'ngRoute',
    'mainCtrl',
    'mapCtrl'
  ]);
  app.config(['$httpProvider','$routeProvider','$locationProvider',function($httpProvider,$routeProvider,$locationProvider) {

    //$routeProvider
    //.when('/', {templateUrl: 'views/main.html', controller: 'mainController'})
    //.otherwise({redirectTo: '/'});

    //$locationProvider.html5Mode(true);
  }]);
  app.run(function(){

    function detectType(input){
      if(input.match(/[a-z]/i)){
        return "CITY_STATE";
      }else{
        return "LONG_LAT";
      }
    }

    $("#search-box").keyup(function(event){
      if(event.keyCode == 13){
        var info = $("#search-box").val().split(",");
        if(detectType($("#search-box").val()) == "CITY_STATE"){
          $.ajax({
            url: "/search",
            type: "POST",
            data: '{"city":"'+info[0]+'", "state":"'+info[1]+'","dist":1}'
          }).done(function(res){
            var obj = JSON.parse(res);
            var map = L.map('map').setView([obj.lat,obj.lon],13);
            var content = "<h3 style='color:black'>"+obj.city + ", " + obj.state + "</h3>";
            content = content + "<br/><strong>Crime Rate:</strong>"+obj.crime;
            main_marker.setLatLng([obj.lat,obj.lon]).addTo(map).bindPopup(content).openPopup();
            setData("#stat-bar",obj.crime);
          });
        }else{
          $.ajax({
            url: "/search",
            type: "POST",
            data: {"lat":info[0], "lon":info[1]}
          }).done(function(res){
            console.log(res);
          });
        }
      }
    });
  });

})();
