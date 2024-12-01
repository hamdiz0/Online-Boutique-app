// build & push function for multiple dockerfiles
// build(Nexus docker-hosted repo url , Version , CredentialId from jenkins , list of ImageName and the corresponding DockerFile path)
def build(String url ,String version ,String credId , List<List> imageName_dockerfilelocation_pair){
    withCredentials([
        usernamePassword(
            credentialsId: "$credId",
            usernameVariable: "USER",
            passwordVariable: "PASSWORD"
        )
    ]){
        sh "echo $PASSWORD | docker login $url -u $USER --password-stdin"
        for (pair in imageName_dockerfilelocation_pair){
            sh "echo building ${pair[0]} image ..."
            sh "docker build ${pair[1]} -t $url/${pair[0]}:$version"
            sh "docker build ${pair[1]} -t $url/${pair[0]}:latest"
        }
    } 
}

// push & push function for multiple dockerfiles
// push(Nexus docker-hosted repo url , Version , CredentialId from jenkins , list of ImageNames)
def push(String url ,String version ,String credId , List<List> imageNames){
    withCredentials([
        usernamePassword(
            credentialsId: "$credId",
            usernameVariable: "USER",
            passwordVariable: "PASSWORD"
        )
    ]){
        sh "echo $PASSWORD | docker login $url -u $USER --password-stdin"
        for (imageName in imageNames){
            sh "echo pushing $imageName ..."
            sh "docker push $url/$imageName:$version"
            sh "docker push $url/$imageName:latest"
        }
    } 
}

// commit and push changes to a git repo 
// git_push( Git repo url without "https://" , CredentialId from jenkins , String commitMsg, String toBranch)
def git_push(String url , String credId , String commitMsg, String branch){
    echo "pushing to $branch ..."
    withCredentials([
        usernamePassword(
            credentialsId:"$credId",
            usernameVariable:'USER',
            passwordVariable:'TOKEN'
        )]){
        sh "git remote set-url origin https://${USER}:${TOKEN}@$url"
        sh "git pull origin $branch"
        sh "git status;git add ."
        sh "git commit -m \"${commitMsg}\"" 
        sh "git push origin HEAD:$branch"
    }
}
return this
