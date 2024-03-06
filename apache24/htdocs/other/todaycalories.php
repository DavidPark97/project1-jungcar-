<?php
    $con=mysqli_connect("localhost","root","1234","everyhealth");
   
    $user_idx =$_POST['user_idx'];
    $date = $_POST['now'];


    // $user = "$_POST['user']";
    // $date = "'".$_POST['date']."'";

    $query1 = "SELECT sum(count*dec_kcal) as total FROM (SELECT count, workout_idx FROM daily_data WHERE user_idx =".$user_idx." and date ='".$date."') as a  inner join workout where a.workout_idx = workout.workout_idx;";
    $query2 = "SELECT round(sum(gram*kcal)) as total FROM (SELECT gram, food_idx FROM daily_eat WHERE user_idx = ".$user_idx." and date ='".$date."') as a inner join food where a.food_idx = food.food_idx;";
   
    $total_work = mysqli_fetch_array(mysqli_query($con, $query1), MYSQLI_ASSOC);
    $total_eat = mysqli_fetch_array(mysqli_query($con, $query2), MYSQLI_ASSOC);
    
    $arr=array("total" => $total_work["total"]-$total_eat["total"]);
    $test=array($arr);
    $jsonData=json_encode(array("webnautes"=>$test), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
   
    echo "$jsonData";

    mysqli_close($con);
?>