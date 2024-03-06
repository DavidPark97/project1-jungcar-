<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$id = $_POST['id'];
$passwd = $_POST['passwd'];


header('content-type: text/html; charset=utf-8');

    $query = "update user set passwd='$passwd' where id='$id'";
    $result=mysqli_query($con, $query);
    




	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();




mysqli_close($con);
?>