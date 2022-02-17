using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using Model;
using Model.Identity;
using Persistence.Database.Config;

namespace Persistence.Database
{
    public class ApplicationDbContext : 
        IdentityDbContext<ApplicationUser, ApplicationRole, string, IdentityUserClaim<string>,
        ApplicationUserRole, IdentityUserLogin<string>,
        IdentityRoleClaim<string>, IdentityUserToken<string>>
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
        {
            
        }

        public DbSet<Distrito> Distritos { get; set; }
        public DbSet<Parqueo> Parqueos { get; set; }
        public DbSet<Hora> Horas { get; set; }
        public DbSet<Alquiler> Alquileres { get; set; }

        protected override void OnModelCreating(ModelBuilder builder) 
        {
            base.OnModelCreating(builder);

            new DistritoConfig(builder.Entity<Distrito>());
            new ParqueoConfig(builder.Entity<Parqueo>());
            new HoraConfig(builder.Entity<Hora>());
            new AlquilerConfig(builder.Entity<Alquiler>());

            new ApplicationUserConfig(builder.Entity<ApplicationUser>());
            new ApplicationRoleConfig(builder.Entity<ApplicationRole>());
        }
    }
}
