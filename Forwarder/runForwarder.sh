# example startup script for the Forwarder

unset AGENTDIR
unset CRS_TAGS
unset JDK_JAVA_OPTIONS
unset _JAVA_OPTIONS
unset AZ_CRS_ARGUMENTS

wget `curl -s https://cdn.azul.com/forwarder/forwarder.json | grep url | cut -d"\"" -f4`

. ../.env

java -jar ./forwarder.jar
