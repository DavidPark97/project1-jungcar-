<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$crash_idx = $_POST['sell_idx'];
$orders = $_POST['orders'];

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "select img as name from crash_img 
where crash_idx=$crash_idx order by img;");
$rowCnt= mysqli_num_rows($result);
$arr= array();
for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}
$name = $arr[$orders]['name'];
$arr2=array("name" => $name,"cnt"=>$rowCnt);
$arr3=array($arr2);
  $jsonData=json_encode(array("webnautes"=>$arr3), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";


mysqli_close($con);
?>