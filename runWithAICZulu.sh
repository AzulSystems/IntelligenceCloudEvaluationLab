
unset CRS_TAGS
unset _JAVA_OPTIONS
unset AZ_CRS_ARGUMENTS
unset AGENTDIR
unset JDK_JAVA_OPTIONS

. ./.env

export CRS_TAGS="AppName=$APPNAME;AppEnv=$APPENV;username=$USERNAME"
export _JAVA_OPTIONS="-XX:+UnlockExperimentalVMOptions -XX:AzCRSMode=on"

# api.url is the Forwarder
# set up for class level code inventory, which is the default
#
# details at https://docs.azul.com/intelligence-cloud/detailed-information/diagnostic-configuration#diagnostics-crs-arguments

export AZ_CRS_ARGUMENTS="enable,log=info,delayTermination=120000,delayInitiation=0,api.url=https://localhost"

java -jar target/spring-petclinic-3.0.0-SNAPSHOT.jar

