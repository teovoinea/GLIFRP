(function(){
  var main_markers = new L.featureGroup();
  var app = angular.module('GLIFRP', [
    'ngRoute',
    'mainCtrl',
    'mapCtrl'
  ]);
  app.config(['$httpProvider','$routeProvider','$locationProvider',function($httpProvider,$routeProvider,$locationProvider,$scope) {
    //$routeProvider
    //.when('/', {templateUrl: 'views/main.html', controller: 'mainController'})
    //.otherwise({redirectTo: '/'});

    //$locationProvider.html5Mode(true);
  }]);
  app.run(function(){
    var currentMarker = null;
    function detectType(input){
      if(input.match(/[a-z]/i)){
        return "STATE";
      }else{
        return "NUMBER";
      }
    }

    $("#search-box").keyup(function(event){
      if(event.keyCode == 13){
        $("#menu").slideUp(40).css({'tab-index':-1});
        var info = $("#search-box").val().split(",");
        /**if(detectType($("#search-box").val()) == "CITY_STATE"){
          $.ajax({
            url: "search",
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
            url: "search",
            type: "POST",
            data: {"lat":info[0], "lon":info[1]}
          }).done(function(res){
            console.log(res);
          });
        }*/

        var marker_click = function(event){
          var obj = event.target.obj;
          setData("#stat-bar",obj.crime/500.0);
          var content = "<h3 style='color:black'>"+obj.name+", "+obj.uState+"</h3><strong>Population:</strong>"+obj.population;
          content = content + "<br/><strong>Longitude:</strong>"+obj.lon;
          content = content + "<br/><strong>Lattitude:</strong>"+obj.lat;
          content = content + "<br/><strong>Overall Score:</strong>"+parseInt(obj.score);
          $("#info-popup-content").html(content);
        };

        var display = function(res){
          var arr = JSON.parse(res);
          for(var i = 0; i < arr.length;i++){
            var obj = arr[i];
            //map.setView([obj.lat,obj.lon],13);
            obj.crime = obj.score;
            var content = "<h3 style='color:black'>"+obj.name + ", " + obj.uState + "</h3>";
            content = content + "<br/><strong>Violent Crime:</strong>"+parseInt(obj.violentCrime);
            content = content + "<br/><strong>Burglary:</strong>"+parseInt(obj.burglary);
            content = content + "<br/><strong>Larceny:</strong>"+parseInt(obj.larceny);
            content = content + "<br/><strong>Rape:</strong>"+parseInt(obj.rape);
            content = content + "<br/><strong>Motor:</strong>"+parseInt(obj.motor);
            content = content + "<br/><strong>Arson:</strong>"+parseInt(obj.arson);
            var mark = L.marker([obj.lat,obj.lon]).bindPopup(content).addTo(map);
            mark.obj = obj;
            mark.on('click',marker_click);
            main_markers.addLayer(mark);
            $("#loading").css({"display":"none"});
          }
          map.fitBounds(main_markers.getBounds().pad(0.5));
        };
        main_markers.eachLayer(function(layer){
          map.removeLayer(layer);
        });
        main_markers.clearLayers();
        var content = "<h3 style='color:black'></h3><strong>No Info</strong>";
        var count;
        $("#loading").css({"display":"flex"});
        if(info.length == 2){
            if(detectType(info[1]) == "STATE"){
                count = 10;
            }else{
                $.ajax({
                  url:"/search",
                  type:"POST",
                  data: '{"count":'+info[1].trim()+',"state":"'+info[0].trim()+'","crime":'+window.crime+',"price":'+window.price+',"distance":'+window.distance+'}'
                }).done(function(res){display(res);});
                return;
            }
        }else if(info.length == 1){
            $.ajax({
              url:"/search",
              type:"POST",
              data: '{"count":10,"state":"'+info[0].trim()+'","crime":'+window.crime+',"price":'+window.price+',"distance":'+window.distance+'}'
            }).done(function(res){display(res);});
            return;
        }else{
            count = info[2];
        }
        $("#info-popup-content").html(content);
        //if(detectType($("#search-box").val()) == "NUMBER"){
          $.ajax({
            url:"/search",
            type:"POST",
            data: '{"count":'+count+',"city":"'+info[0].trim()+'","state":"'+info[1].trim()+'","crime":'+window.crime+',"price":'+window.price+',"distance":'+window.distance+'}'
          }).done(function(res){display(res);});
        /*}else{
          $.ajax({
            url:"/searchLCMByState",
            type:"POST",
            data: '{"count":'+info[0]+',"state":"'+info[1].trim()+'"}'
          }).done(function(res){console.log(res);display(res);});
      }*/
  }else{
      window.onInputFocus();
  }
    });
  });

})();
