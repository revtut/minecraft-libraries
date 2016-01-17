#!/bin/sh

BASE="$HOME/Repository/Minecraft - Libraries"
cd "$BASE"

tmux start-server
tmux new-session -d -s dev-mc-libraries -n Terminal
tmux new-window -t dev-mc-libraries:2 -n Vim
tmux new-window -t dev-mc-libraries:3 -n Gradle
tmux new-window -t dev-mc-libraries:4 -n Git

tmux send-keys -t dev-mc-libraries:2 "vim \"$BASE/src\"" C-m
tmux send-keys -t dev-mc-libraries:3 "gradle build" C-m
tmux send-keys -t dev-mc-libraries:4 "git status" C-m

tmux select-window -t dev-mc-libraries:1
tmux attach-session -t dev-mc-libraries
