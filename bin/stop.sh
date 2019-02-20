#!/usr/bin/env bash
ps -ef |grep 'easy-create' |awk '{print $2}'|xargs kill -9