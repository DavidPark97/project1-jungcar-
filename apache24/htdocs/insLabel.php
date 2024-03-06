<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$idx = $_POST['idx'];
$sell_idx =  $_POST['sell_idx'];

$now = date("Y-m-d H:i:s");



header('content-type: text/html; charset=utf-8');



if($idx==="999"){
   
    $query = "select * from updatelist where sell_idx = $sell_idx";
    $rowCnt = 0;
    $result=mysqli_query($con, $query);
    $rowCnt= mysqli_num_rows($result);
    if($rowCnt!=0){
        $query2 = "update updatelist set stddate='$now' where sell_idx=$sell_idx";
        $result2=mysqli_query($con, $query2);
        $query3 = "update car_market set date='$now' where sell_idx=$sell_idx";
        $result3=mysqli_query($con, $query3);
    }else{
        $query2 = "insert into updatelist (sell_idx,stddate) values ($sell_idx,'$now');";
        $result2=mysqli_query($con, $query2);
        
    }
}else{

       $query2 = "insert into additional (sell_idx,label_idx) values ($sell_idx,$idx)";
       $result2=mysqli_query($con, $query2);
    }


	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();





mysqli_close($con);
?>