<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$option_type_idx = $_POST['option_type_idx'];
$connection_idx = $_POST['connection_idx'];

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "select option_name as name, optionn.option_idx as idx from connection, optionn, option_connection 
where connection.connection_idx = option_connection.connection_idx 
and option_connection.option_idx = optionn.option_idx and connection.connection_idx = $connection_idx 
and option_type_idx = $option_type_idx order by idx;");
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>