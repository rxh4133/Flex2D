#version 450

in vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform int useColor;
uniform vec3 color;

void main()
{
	if(useColor == 1){
		fragColor = vec4(color, 1);
	}else{
		fragColor = texture(texture_sampler, outTexCoord);
	}
}