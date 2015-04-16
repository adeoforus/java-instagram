<?php
namespace Client\Component\Server;

class Client {

    private $addressServer;
    private $portServer;
    private $address;
    private $port;
    private $command;

    public function __construct($port, $address, $addressServer, $portServer, $command)
    {
        set_time_limit(0);
        $this->addressServer = $addressServer;
        $this->address = $address;
        $this->portServer = $portServer;
        $this->port = $port;
        $this->command = $command;
    }

    public function init(){

        //create socket
        if(! $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)){
            $this->showError('socket create');
        }
        // echo  "Server Created\n";

        //bind socket
        if(!socket_bind($socket, $this->address, $this->port)){
            $this->showError('socket bind');
        }
        // echo  "Server bind to $this->address and $this->port \n";

        //After binding
        //Send Request to Java Server
        $request = new Request(
            $this->addressServer, $this->portServer, $this->command
        );


        if(!socket_listen($socket)){
            $this->showError('socket listen');
        }
        // echo  "Server Listening \n";

//        do{
        $client = socket_accept($socket);
        // echo  "connection established\n";

        if(!$clientMessage = socket_read($client, 10000, PHP_NORMAL_READ)){
            $this->showError('socket read');
        }

        // echo  "Command Received\n";
        // echo  $clientMessage;

        return $clientMessage;

//        }while(true);


    }

    private function showError($message){
        // echo  ("Error: ".$message);
        exit(666);
    }
}

//$portClient = $addressClient = $addressServer = $portServer = null;
//$type_request_client = $seperator_client = null;
//require_once dirname(__DIR__) . '/../../../vendor/autoload.php';
//include __DIR__.'/../../Resources/config/parameters.php';
//
//// echo  "Start \n";
//$task = [
//    'tag'=>'barcelona',
//    'sort'=>0,
//    'cloud'=>0,
//    'DATA_IN_FOLDER'=>null,
//    'DATA_OUT_FOLDER'=>null,
//    'MAP_FOLDER'=>null,
//    'task_id'=>0,
//    'status'=>'pending',
//    'result'=>'',
//    'completed_state'=>0
//];
//$json = json_encode($task);
//
//$command = $type_request_client . $seperator_client . $json . "\n";
//$client = new Client($portClient, $addressClient, $addressServer, $portServer, $command);
//
