<?php
$con=mysqli_connect("localhost","root","1234","jungcar");




$car_idx = $_POST['car_idx'];
$center_idx = $_POST['center_idx'];
$star = $_POST['star'];
$comment = $_POST['comment'];


$now = date("Y-m-d");


header('content-type: text/html; charset=utf-8');




	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

    $query = "insert into center_review(center_idx,car_idx,star,comment,date) values ($center_idx,$car_idx,$star,'$comment','$now')";


$result=mysqli_query($con, $query);


mysqli_close($con);
?>