# Build
custom_build(
    # Name of container image
    ref = 'catalog-service',
    # Command to build container image
    command = 'mvn spring-boot:build-image -Dproject.image.name=$EXPECTED_REF',
    # Files to watch that trigger a new build
    deps = ['pom.xml', 'src']
)

# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# Manager
k8s_resource('catalog-service', port_forwards=['8080'])