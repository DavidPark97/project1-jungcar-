<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$sell_idx = $_POST['sell_idx'];
$orders = $_POST['orders'];

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "select car_img.img as name from car_img, car, car_market 
where car_img.car_idx = car.car_idx and car.car_idx = car_market.car_idx and sell_idx=$sell_idx order by car_img.img;");
$rowCnt= mysqli_num_rows($result);
$arr= array();
for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}
$name = $arr[$orders-1]['name'];
$arr2=array("name" => $name,"cnt"=>$rowCnt);
$arr3=array($arr2);
  $jsonData=json_encode(array("webnautes"=>$arr3), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";


mysqli_close($con);
?>