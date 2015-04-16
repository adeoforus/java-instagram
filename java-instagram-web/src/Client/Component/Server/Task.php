<?php
namespace Client\Component\Server;

class Task {

    private $tags;
    private $sort;
    private $cloud;
    private $DATA_OUT_FOLDER;
    private $DATA_IN_FOLDER;
    private $MAP_FOLDER;
    private $task_id;
    private $status;
    private $result;
    private $completed_state;

    public function __construct($tags, $sort, $cloud){
        $this->tags = $tags;
        $this->sort = $sort;
        $this->cloud = $cloud;
        $this->DATA_IN_FOLDER="";
        $this->DATA_OUT_FOLDER="";
        $this->MAP_FOLDER="";
        $this->task_id=0;
        $this->status="pending";
        $this->result="";
        $this->completed_state=0;
    }

    public function getCommand(){
        return [
            'tags'=>$this->tags,
            'sort'=>$this->sort,
            'cloud'=>$this->cloud
        ];
    }



}