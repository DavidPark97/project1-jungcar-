<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$sell_idx = $_POST['sell_idx'];

$result = mysqli_query($con, "select car_idx from car_market where sell_idx=$sell_idx");
$car = mysqli_fetch_array($result);
$car_idx = $car['car_idx'];



mysqli_query($con,"delete from additional where sell_idx = $sell_idx");
mysqli_query($con,"delete from bookmark where sell_idx = $sell_idx");
mysqli_query($con,"delete from  sell_option where sell_idx = $sell_idx");
mysqli_query($con,"delete from  sh_history where sell_idx = $sell_idx");
mysqli_query($con,"delete from  updatelist where sell_idx = $sell_idx");


$result2=mysqli_query($con, "select sell_comment_idx from sell_comment where sell_idx = $sell_idx");
$rowCnt= mysqli_num_rows($result2);


for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
      $comment_idx = $row['sell_comment_idx'];
      mysqli_query($con,"delete from sell_recomment where sell_comment_idx = $comment_idx");
}
mysqli_query($con,"delete from sell_comment where sell_idx = $sell_idx");
mysqli_query($con,"delete from car_market where sell_idx = $sell_idx");
mysqli_query($con,"delete from car_own where car_idx = $car_idx");

$result3=mysqli_query($con, "select img from car_img where car_idx = $car_idx");
$rowCnt2= mysqli_num_rows($result3);


for($i=0;$i<$rowCnt2;$i++){
      $row= mysqli_fetch_array($result3, MYSQLI_ASSOC);
      $img = $row['img'];
      unlink("$img");
}
mysqli_query($con,"delete from car_img where car_idx = $car_idx");

$result4=mysqli_query($con, "select img from car where car_idx = $car_idx");
$row= mysqli_fetch_array($result4, MYSQLI_ASSOC);
$img = $row['img'];
unlink("$img");
mysqli_query($con,"delete from car where car_idx = $car_idx");

mysqli_close($con);
?>