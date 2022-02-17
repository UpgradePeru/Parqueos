using Microsoft.AspNetCore.Mvc;

namespace Core.Api.Controllers
{
    [ApiController]
    [Route("/")]
    public class DefaultController : ControllerBase
    {
        public string Index() 
        {
            return "{ Taller de Proyectos 1 - Grupo 5 } Running ... ";
        }

    }

}

