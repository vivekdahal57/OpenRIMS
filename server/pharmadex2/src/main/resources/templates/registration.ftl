<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:500">
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
        }

        /* Full-width input fields */
        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        /* Set a style for all buttons */
        button {
            width: 100%;
            height: 42px;
            background-color: #4285f4;
            color: #fff;
            cursor: pointer;
            padding: 14px 20px;
            margin: 8px 0;
            border-radius: 2px;
            border: none;
            box-shadow: 0 3px 4px 0 rgba(0, 0, 0, .25);
        }

        button:hover {
            opacity: 0.8;
        }

        ul {
            display: block;
            list-style-type: disc;
            margin-block-start: 1em;
            margin-block-end: 1em;
            margin-inline-start: 0px;
            margin-inline-end: 0px;
            padding-inline-start: 0px;
        }

        /* Extra styles for the cancel button */
        .cancelbtn {
            width: auto;
            padding: 10px 18px;
            background-color: #f44336;
        }

        /* Center the image and position the close button */
        .imgcontainer {
            text-align: center;
            margin: 24px 0 12px 0;
            position: relative;
        }

        img.avatar {
            width: 40%;
            border-radius: 50%;
        }

        .container {
            display: flex;
        }

        .first_flex {
            flex: 2;
            padding: 10px;
        }

        .languages {
            display: flex;
            flex-flow: row-reverse wrap;
        }

        .language {
            width: 40px;
            padding: 5px
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }

        /* The Modal (background) */
        .modal {
            display: block; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0, 0, 0); /* Fallback color */
            background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
            padding-top: 60px;
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
            border: 1px solid #888;
            width: 30%; /* Could be more or less, depending on screen size */
        }


        /* Add Zoom Animation */
        .animate {
            -webkit-animation: animatezoom 0.6s;
            animation: animatezoom 0.6s
        }

        @-webkit-keyframes animatezoom {
            from {
                -webkit-transform: scale(0)
            }
            to {
                -webkit-transform: scale(1)
            }
        }

        @keyframes animatezoom {
            from {
                transform: scale(0)
            }
            to {
                transform: scale(1)
            }
        }

        /* Change styles on extra small screens */
        @media screen and (max-width: 300px) {
            span.psw {
                display: block;
                float: none;
            }

            .modal-content {
                width: 80%; /* Could be more or less, depending on screen size */
            }
        }

        .google-btn {
            width: 100%;
            height: 42px;
            background-color: #4285f4;;
            cursor: pointer;
            cursor: hand;
            border-radius: 2px;
            box-shadow: 0 3px 4px 0 rgba(0, 0, 0, .25);
        }

        .google-icon-wrapper {
            position: absolute;
            margin-top: 1px;
            margin-left: 1px;
            width: 40px;
            height: 40px;
            border-radius: 2px;
            background-color: #fff;
        }

        .google-icon {
            position: absolute;
            margin-top: 11px;
            margin-left: 11px;
            width: 18px;
            height: 18px;
        }

        .btn-text {
            float: right;
            margin: 11px 11px 0 0;
            color: #fff;
            font-size: 14px;
            letter-spacing: 0.2px;
            font-family: "Roboto";
        }

        &
        input:hover {
            box-shadow: 0 0 6px #4285f4;;
        }

        &
        :active {
            background: #1669F2;
        }

        .success {
            color: green
        }

        .failure {
            color: red
        }

        @keyframes blink {
            50% {
                opacity: 0.0;
            }
        }

        .blink {
            color: blue;
            font-weight: bold;
            font-size: 2em;
            animation: blink 1s step-start 0s infinite;
        }
    </style>
</head>
<body>
<script>
    window.onsubmit=function(){
        document.cookie="username="+document.getElementById('username').value+";max-age=8640"
    }
</script>
<div id="id01">
    <div class="container modal-content animate">
        <div class="first_flex">
            <h2>Guest User Registration Form</h2>
            <form id="form" action="externalUser" method="POST">
                <div class="languages">
                    <#list languages?keys as key>
                        <div class="language">
                            <a href="?lang=${key}">
                                <img src="/api/public/flag?localeStr=${key}" width="30"/>
                            </a>
                        </div>
                    </#list>
                </div>
                <label for="fullName"><b>${fullNameLabel}</b></label>
                <input type="text" id="name" name="name" required value=${name}>
                <label for="email"><b>${emailLabel}</b></label>
                <input type="text" id="email" name="email" required value=${email}>
                <label for="address"><b>${addressLabel}</b></label>
                <input type="text" id="address" name="address" required value=${address}>
                <input type="hidden" id="enabled" name="enabled" value=1>
                <label for="phone"><b>${phoneLabel}</b></label>
                <input type="text" id="phone" name="phone" required value=${phone}>
                <label for="psw"><b>${passwordLabel}</b></label>
                <input type="password" name="password" id="password" required>
                <button type="submit">${registration}</button>
            </form>
            <form id="form" action="registrationLogin" method="GET">
                <button type="submit">${login}</button>
            </form>
            <br/> <br/><br/>
            <div id="message">
            </div>
        </div>

</div>
</body>
</html>
