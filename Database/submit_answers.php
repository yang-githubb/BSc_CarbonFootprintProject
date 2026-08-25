<?php
require "auth.php";

// Saves all survey answers for the authenticated user in one transaction.
// Expects POST "answers": a JSON array of {"questionId": int, "optionIndex": int}.
list($db, $userId) = requireAuthenticatedUser();

$answers = json_decode($_POST['answers'] ?? '', true);
if (!is_array($answers) || count($answers) === 0) {
    echo "Invalid answers payload";
    exit;
}
foreach ($answers as $answer) {
    if (!isset($answer['questionId']) || !isset($answer['optionIndex'])) {
        echo "Invalid answers payload";
        exit;
    }
}

echo $db->saveAnswers($userId, $answers) ? "Save Success" : "Save Failed";
?>
