<?php
    $con=mysqli_connect("localhost","root","1234","everyhealth");
    
    $user = $_POST['user_idx'];
    //$prefer = "5";
    //$inbody = "70";
    //$inbody2 = $inbody + 4;
    //$gender = "1";

    $query1 = "SELECT * FROM user WHERE user_idx=$user;";
    $result = mysqli_query($con, $query1);
    $query1 = "SELECT preference, inbody, gender FROM user WHERE user_idx =".$user.";";
    $result = mysqli_fetch_array(mysqli_query($con, $query1));
    
    $prefer = $result["preference"];
    $inbody = $result["inbody"];
    $gender = $result["gender"];
    if($inbody>=100){
      $inbody=100;
      $inbody2=110;
    }else{
    $a = 5;
  $inbody = intval($inbody/$a);
  $inbody = intval($inbody*5);
  $inbody2 = $inbody+4;
  }
    
  $query2 = "SELECT * FROM user WHERE preference = ".$prefer." AND gender = ".$gender." AND inbody between ".$inbody." and ".$inbody2.";";
  $result2 = mysqli_query($con, $query2);

  $rowCnt = mysqli_num_rows($result2);
  $randnum = rand(1, $rowCnt);
    
    
    $result3=mysqli_query($con, "select r.user_idx, w.workout_idx, w.workout_name, w.img, r.targets, r.weight from workout as w, routine as r where r.routine_idx = 1 and r.user_idx=$randnum and r.workout_idx = w.workout_idx order by r.orders");
    $rowCnt3= mysqli_num_rows($result3);
    $arr= array();

for($i=0;$i<$rowCnt3;$i++){
      $row= mysqli_fetch_array($result3, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

    
    echo "$jsonData";

    mysqli_close($con);
?>