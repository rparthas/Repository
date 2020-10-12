<?php
echo "Hello World!";
$language = "PHP";
$version = 7.3;
echo $language;
echo $version;
// $name = $argv[1];
// $name = $_GET['name'];
// echo "Hello ". $name;
echo '<pre>';
print_r($_SERVER);
echo '</pre>';

$name1 = '';

$name2 = null;

echo 'checking $name1 : ';
var_dump(isset($name1));

echo '<br>';

echo 'checking $name2: ';
var_dump(isset($name2));

echo '<br>';

echo 'checking undeclared variable $name3: ';
var_dump(isset($name3));

$str = 'Your order total is: ';
$order = 20;
$additional = 5;
$orderTotal = 0;
$complete = false;
$orderTotal = $order + $additional;
if ($orderTotal >= 25) {
        $complete = true;
        echo $str . $orderTotal;
}
$names = ['Dave','Kerry','Dan','Jack','James','Ruby','Sam','Teresa','Tony'];
echo $names[3];
$heroInfo = [
    [
          'heroName' => 'Spiderman',
          'weapon' => 'Spider web'
    ],
    [
          'heroName' => 'Iron Man',
          'weapon' => 'Mark L'
    ],
    [
	  'heroName' => 'Thor',
          'weapon' => 'Mjolnir'
    ],
    [
	  'heroName' => 'Captain America',
          'weapon' => 'Shield'
    ]
];
print_r($heroInfo[2]['weapon']);

// $name = $argv[1];
// $heightMeters = (int) $argv[2];
// $heightCentiMeters = (int) $argv[3];
// $cmToMeter = (float)($heightCentiMeters/100);

// $finalHeightInMeters = $heightMeters + $cmToMeter;
// echo $name . ': ' . $finalHeightInMeters . 'm';
?>