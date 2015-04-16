<?php
require_once dirname(__DIR__) . '/../../vendor/autoload.php';

/*
 *  Rendering Template
 */
//TODO make asset manager work!
//require __DIR__."/../Component/Twig/template.php";

Twig_Autoloader::register();
$loader = new Twig_Loader_Filesystem('./src/Client/Resources/views');
if($mode=='prod'){
    $twig = new Twig_Environment($loader,array(
        'cache'=>'./tmp'
    ));
}else{
    $twig = new Twig_Environment($loader,array());
}


/**
 * Handling Form
 */
$results=[];
if(isset($_GET['tag'])){
    require_once dirname(__DIR__) . '/../../vendor/autoload.php';
    include __DIR__.'/../Resources/config/parameters.php';

    $tag = $_GET['tag'];

    $task = [
        'tag'=>trim($tag),
        'sort'=>0,
        'cloud'=>0,
        'DATA_IN_FOLDER'=>null,
        'DATA_OUT_FOLDER'=>null,
        'MAP_FOLDER'=>null,
        'task_id'=>0,
        'status'=>'pending',
        'result'=>'',
        'completed_state'=>0
    ];
    $json = json_encode($task);

    $command = $type_request_client . $seperator_client . $json . "\n";
    $client = new \Client\Component\Server\Client($portClient, $addressClient, $addressServer, $portServer, $command);
    $message = trim($client->init());

    //testing
//    $message = "from_slave;tmp_output\\SLAVE_0\\0";

    $command = explode(";",$message);
    $location = $command[1];
    $file = fopen($location_server.$location,"r");

    while(!feof($file)) {
        $line = fgets($file);
        $obj = json_decode($line);
        $results[] = $obj;
    }

    //If exist sort
    if(isset($_GET['sort'])){
        $sort = $_GET['sort'];
    }

    //If exist cloud
    if(isset($_GET['cloud'])){
        $cloud = $_GET['cloud'];
    }

}

//testing
//var_dump($results[0]);

$template = $twig->loadTemplate('index.html.twig');
$router = new App\Router();
$params = array();
$template->display(array(
    'param'=>$params,
    'router'=>$router->getRouter(),
    'results'=>$results
));


