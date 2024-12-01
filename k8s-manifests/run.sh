#!/bin/bash

DIR=. # set the directory to the default

echo "applying k8s yaml files"

for file in "$DIR"/*.y*ml; do
    if [ -f "$file" ]; then
        echo "applying $file ..."
        kubectl apply -f "$file"
    else
        echo "no yaml files in : $DIR"
        break
    fi
done

echo -e "\nstarting pods ...\n"

kubectl rollout status all

kubectl rollout restart deployment/coredns --namespace=kube-system # restart coredns to avoid dns issues (pods can't recognize service names)

echo -e "\nkubectl get all :\n"
kubectl get all
