<?php


$portClient = $addressClient = $addressServer = $portServer = null;
$type_request_client = $seperator_client = null;
require_once dirname(__DIR__) . '/../../../vendor/autoload.php';
include __DIR__.'/../../Resources/config/parameters.php';

echo "Start";
$task = [
    'tag'=>'barcelona',
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
$command = $type_request_client . $seperator_client . $json;


$request = new Client\Component\Server\Request(
    $addressServer, $portClient, $command
);