angular.module("app", []).controller("home", function($http, $location, $window) {
    var self = this;
    $http.get("/user").success(function(data) {
        self.user = data.userAuthentication.details.name;
        self.authenticated = true;
        $http.get("/user/socialnetworks").success(function(data) {
            self.socialNetworks = data;
        }).error(function() {
            console.log("Error getting user's social networks");
        });
    }).error(function() {
        self.user = "N/A";
        self.authenticated = false;
    });
    self.logout = function() {
        $http.post("/logout", {}).success(function() {
            self.authenticated = false;
            $location.path("/");
        }).error(function(data) {
            console.log("Logout failed");
            self.authenticated = false;
        });
    };
    self.authorizeTwitter = function(name) {
        $http.get("/auth/twitter").success(function(data) {
            $window.location.href = data.url;
            createChips(name);
        }).error(function(data) {
            console.log(data);
            console.log("Twitter auth failed");
        });
    };
});
