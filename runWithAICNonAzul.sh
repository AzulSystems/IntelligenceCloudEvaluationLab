unset AGENTDIR
unset CRS_TAGS
unset JDK_JAVA_OPTIONS
unset AZ_CRS_ARGUMENTS
unset _JAVA_OPTIONS

. ./.env

AGENTDIR=./Agent

cd $AGENTDIR
wget `curl -s https://cdn.azul.com/ic-agent/ic-agent.json | grep url | cut -d"\"" -f4`
unzip *.zip
cd ..

export CRS_TAGS="AppName=$APPNAME-Agent;AppEnv=$APPENV-Agent;usingagent=yes; username=$USERNAME"

export JDK_JAVA_OPTIONS="-javaagent:${AGENTDIR}/ic-agent.jar=accessor=${AGENTDIR}/ic-agent-accessor.jar \
   -DaagentDir=${AGENTDIR} \
   -Djava.security.policy=${AGENTDIR}/agent.all.policy"

# set up for class level code inventory, which is the default. 
# Details at https://docs.azul.com/intelligence-cloud/detailed-information/diagnostic-configuration#diagnostics-crs-arguments

export AZ_CRS_ARGUMENTS="log=info,UnlockExperimentalCRS,enable,api.url=https://localhost,delayInitiation=0,delayTermination=120000"

java -jar target/spring-petclinic-3.0.0-SNAPSHOT.jar

