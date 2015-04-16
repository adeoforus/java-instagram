<?php
namespace Client\Component\Server;

class Request {

    public function __construct($address, $port, $command)
    {
        set_time_limit(0);
        $this->address = $address;
        $this->port = $port;
        $this->command = $command;
        $this->init();
    }

    public function init(){
        if(! $socket = socket_create(AF_INET, SOCK_STREAM, getprotobyname('tcp'))){
            $this->showError("socket create");
        };

        socket_connect($socket, $this->address, $this->port);

        $message = $this->command."\n";
        socket_write($socket, $message, strlen($message)); //Send data

        socket_close($socket);

    }

    /**
     * Show error
     * @param $message
     */
    private function showError($message){
        echo ("Error: ".$message);
        exit(666);

    }
}