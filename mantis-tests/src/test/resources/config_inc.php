<?php
$g_hostname               = 'localhost';
$g_db_type                = 'mysqli';
$g_database_name          = 'bugtracker';
$g_db_username            = 'root';
$g_db_password            = '';

$g_default_timezone       = 'Europe/Berlin';

$g_crypto_master_salt     = 'sARJNmrdvC0Jy6+GGVJDSEPpuD4pwgZYupY/CBYlLe0=';

$g_signup_use_captcha     = OFF;
$g_phpmailer_method       = PHPMAILER_METHOD_SMTP;
$g_smtp_host              = 'localhost';

$g_log_level = LOG_EMAIL | LOG_EMAIL_RECIPIENT;
$g_log_destination = 'file:c:/XAMPP/htdocs/mantisbt-1.3.17/logs/mantisbt.log'; //for windows