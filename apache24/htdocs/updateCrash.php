<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$crash_idx = $_POST['crash_idx'];
$date = $_POST['date'];
$location = $_POST['location'];
$memo = $_POST['memo'];





header('content-type: text/html; charset=utf-8');



$query = "update crash_history set date='$date',location='$location',memo='$memo' where crash_idx=$crash_idx";


$result2=mysqli_query($con, "select img from crash_img where crash_idx = $crash_idx");
$rowCnt= mysqli_num_rows($result2);

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result2, MYSQLI_ASSOC);
      $img = $row['img'];
      unlink("$img");
}
mysqli_query($con,"delete from crash_img where crash_idx = $crash_idx");



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);



mysqli_close($con);
?>