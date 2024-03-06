<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

//파일 img라는 파일을 받아온다 (안드로이드로 부터)
$img = $_POST['img'];
$flag = $_POST['flag'];
$crash_idx = $_POST['crash_idx'];
//서버에 저장할 파일내용
$filename = "./crash/cr".$crash_idx."_".$flag.".jpg";
file_put_contents($filename,base64_decode($img));



if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


    $result=mysqli_query($con, "insert into crash_img (crash_idx,img) values ($crash_idx,'$filename')");




?>