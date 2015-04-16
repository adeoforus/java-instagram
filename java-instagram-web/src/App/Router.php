<?php
namespace App;

class Router {
    private $routes;
    private $base;

    public function __construct(){
        $this->routes=[
            'base'=>'/java-instagram-web',
            'homepage'=>'/',
            'search'=>'/search/'
        ];
    }

    public function getRoutes($key){
        return $this->routes[$key];
    }

    public function getRouter(){
        return $this->routes;
    }
}