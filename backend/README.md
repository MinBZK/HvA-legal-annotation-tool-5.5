To run the backend with docker: 
1. Go to maven -> lifecycle -> clean and then install to generate a Jar file.
2. Run "docker build -t backend ." to build a docker image.
3. Run "docker run -p 8085:8085 backend" to run a container.
