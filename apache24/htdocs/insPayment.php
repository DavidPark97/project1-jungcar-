<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$user_idx = $_POST['user_idx'];
$payment = $_POST['payment'];


$now = date("Y-m-d H:i:s");


header('content-type: text/html; charset=utf-8');


    $query2 = "insert into payment (user_idx,payment,date) values ($user_idx,$payment,'$now')";

    $result2=mysqli_query($con, $query2);


mysqli_close($con);
?>