<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

//파일 img라는 파일을 받아온다 (안드로이드로 부터)
$img = $_POST['img'];
$order = $_POST['order'];
$board_idx = $_POST['board_idx'];
$type = $_POST['type'];
//서버에 저장할 파일내용
$filename = "./board/br".$board_idx."_".$order.".jpg";
file_put_contents($filename,base64_decode($img));



if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();


$result = mysqli_query($con, "insert into board_content (board_idx,cont,type) values($board_idx,'$filename',$type)");




?>