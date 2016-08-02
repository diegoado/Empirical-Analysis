#! /bin/bash

while getopts a:i:o:t: OPTION; do
     case "${OPTION}" in
     	a) ALGORITHM=${OPTARG} ;;
        i) INPUTFILE=${OPTARG} ;;
        o) OUTPUTFILE=${OPTARG} ;;
        ?) echo "Invalid option. Please read the README file."; return; ;;
     esac
 done
  
if [ -z $ALGORITHM ] 
then
	echo "Algorithm value is required. See README file."
	return
fi

if [ $ALGORITHM = 1 ]
then
	$ALGORITHM = "sequential"
else
	$ALGORITHM = "concurrent"
fi
  
# --- Clean
#rm -rf ./bin/*

# --- Build
#echo "Compiling files"
#javac -d ./bin ./src/*/*.java

# --- Run
if [ ! -z $INPUTFILE ] 
then
	if [ ! -f $INPUTFILE ]
	then 
		echo "Input file does not exist."
		return
	fi
	#cp -R $INPUTFILE bin/
fi

echo $INPUTFILE $OUTPUTFILE
case $ALGORITHM in 
1)
	java -cp out/ -Xmx2048m production/EmpiricalAnalysis/Executor $ALGORITHM $INPUTFILE $OUTPUTFILE
;;
2)
	java -cp out/ -Xmx2048m production/EmpiricalAnalysis/Executor $ALGORITHM $INPUTFILE $OUTPUTFILE
;;
*)
	echo "Wrong usage. Please, open README file and check." 
	return
;;
esac

if [ ! -z $OUTPUTFILE ] 
then
	cp $OUTPUTFILE ../
    	rm $OUTPUTFILE
fi  
