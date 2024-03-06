<?php

//파일 img라는 파일을 받아온다 (안드로이드로 부터)
$img = $_POST['img'];
$flag = $_POST['flag'];
$user_idx = $_POST['user_idx'];
//서버에 저장할 파일내용
$filename = "plnumber/".$user_idx."_".$flag.".jpg";
file_put_contents($filename,base64_decode($img));









?>