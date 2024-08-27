
unset AGENTDIR
unset CRS_TAGS
unset JDK_JAVA_OPTIONS
unset _JAVA_OPTIONS
unset AZ_CRS_ARGUMENTS

source ./.env

java -jar ./icclient.jar unused-code-diff --appenv=$APPENV --ic=$IC_API_URL --key=$APIKEY --output=./$APPENV-CI_Report
