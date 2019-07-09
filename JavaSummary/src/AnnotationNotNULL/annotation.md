# Blog-@NotEmpty、@NotBlank、@NotNull的区别
总结三种非空注解

## @NotEmpty
@NotEmpty的String类、Collection、Map、数组，是不能为null或者长度为0的（String、Collection、Map的isEmpty()方法）。

## @NotBlank

Validate that the annotated string is not {@code null} or empty.
The difference to {@code NotEmpty} is that trailing whitespaces are getting ignored.
@author Hardy Ferentschik


"The difference to {@code NotEmpty} is that trailing whitespaces are getting ignored."-->>纯空格的String也是不符合规则的。所以才会说@NotBlank用于String。

## @NotNull
/**
* The annotated element must not be {@code null}.
* Accepts any type.
*
* @author Emmanuel Bernard
*/

