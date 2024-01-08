/*
 * *
 *  * Created by Kolawole Omirin
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 18/07/2021, 9:29 AM
 *
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.revnorth.common.webdtos.response;

import lombok.Data;


@Data
public class HttpClientResponse {
    private int code;
    private String status;
    private String message;
    private String payload;
}
