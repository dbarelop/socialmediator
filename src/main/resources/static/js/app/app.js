angular.module('app', [])
    .controller('home', ['$http', '$location', '$window', function($http, $location, $window) {
        var self = this;
        self.activeTab = 'home';
        $http.get('/user').success(function(data) {
            self.user = data.userAuthentication.details.name;
            self.authenticated = true;
            $http.get('/user/socialnetworks').success(function(data) {
                self.socialNetworks = data;
            }).error(function() {
                console.log('Error getting user\'s social networks');
            });
        }).error(function() {
            self.user = 'N/A';
            self.authenticated = false;
        });
        self.logout = function() {
            $http.post('/logout', {}).success(function() {
                self.authenticated = false;
                $location.path('/');
            }).error(function(data) {
                console.log('Logout failed');
                self.authenticated = false;
            });
        };
        self.authorizeTwitter = function() {
            $http.get('/auth/twitter').success(function(data) {
                $window.location.href = data.url;
            }).error(function(data) {
                console.log(data);
                console.log('Twitter auth failed');
            });
        };
        self.authorizeFacebook = function() {
            $http.get('/auth/facebook').success(function(data) {
                $window.location.href = data.url;
            }).error(function(data) {
                console.log(data);
                console.log('Facebook auth failed');
            });
        };
        self.authorizeGoogle = function() {
            $http.get('/auth/google').success(function(data) {
                $window.location.href = data.url;
            }).error(function(data) {
                console.log(data);
                console.log('Google auth failed');
            });
        };
    }])
    .controller('feed', ['$http', '$location', function($http, $location) {
        var self = this;
        self.activeTab = 'feed';
        self.page = 1;
        self.tagFilter = '';
        var social_network = $location.search().social_network;
        var url = '/posts/latest';
        url += social_network ? '?socialNetwork=' + social_network : '';
        $http.get(url).success(function(data) {
            self.posts = data;
        }).error(function() {
            console.log('Error getting posts');
        });
        self.logout = function() {
            $http.post('/logout', {}).success(function() {
                self.authenticated = false;
                $location.path('/');
            }).error(function(data) {
                console.log('Logout failed');
                self.authenticated = false;
            });
        };
        self.previousPage = function() {
            self.posts = [];
            $http.get('/posts/page/' + (++self.page)).success(function(data) {
                self.posts = data;
            }).error(function() {
                console.log('Error getting posts page #' + self.page);
            });
        };
        self.nextPage = function() {
            self.posts = [];
            $http.get('/posts/page/' + (--self.page)).success(function(data) {
                self.posts = data;
            }).error(function() {
                console.log('Error getting posts page #' + self.page);
            });
        };
        self.filter = function() {
            self.posts = [];
            var url = '/posts/latest';
            url += social_network ? '?socialNetwork=' + social_network : (self.tagFilter ? '?tag=' + self.tagFilter : '');
            url += self.tagFilter ? '&tag=' + self.tagFilter : '';
            $http.get(url).success(function(data) {
                self.posts = data;
            }).error(function() {
                console.log('Error filtering posts');
            });
        };
    }]);
