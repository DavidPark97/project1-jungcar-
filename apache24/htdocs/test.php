<?php

//파일 img라는 파일을 받아온다 (안드로이드로 부터)
$user_idx = 1;
$size = 7;
//파이썬 실행

$arr= array("plnumber"=>$plnumber);
$arr2 = array($arr);

$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo "$jsonData";


?>