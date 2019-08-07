job('NodeJS example') {
    scm {
        git('git@github.com:SapirH/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('SapirH')
            node / gitConfigEmail('sapir.holzman@gmail.com ')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('newNodeJs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        shell('npm install')
    }
}

job('NodeJS Docker example') {
    scm {
        git('git@github.com:SapirH/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('SapirH')
            node / gitConfigEmail('sapir.holzman@gmail.com ')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('newNodeJs') 
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('SapirH/docker-cicd') //qa / dev
            tag('${GIT_REVISION,length=9}')
            registryCredentials('dockerhub')
            buildContext('./basic')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}
pipelineJob('boilerplate-pipeline') {
  definition {
    cpsScm {
        scm{
            git('git@github.com:SapirH/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
                node / gitConfigName('SapirH')
                node / gitConfigEmail('sapir.holzman@gmail.com ')
            }
            scriptPath('./basic/misc/Jenkinsfile') 
        }   
    }
  }
}