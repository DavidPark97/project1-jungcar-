<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$user = $_POST['user'];
$sell_idx = $_POST['sell_idx'];


$now = date("Y-m-d H:i:s");


header('content-type: text/html; charset=utf-8');



$query = "select * from sh_history where user_idx = $user and sell_idx =$sell_idx";

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();
$rowCnt = 0;
$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);

if($rowCnt!=0){
    $query2 = "update sh_history set show_date='$now' where user_idx=$user and sell_idx=$sell_idx";
}else{
    $query2 = "insert into sh_history (user_idx,sell_idx,show_date) values ($user,$sell_idx,'$now')";
}
$result2=mysqli_query($con, $query2);


mysqli_close($con);
?>